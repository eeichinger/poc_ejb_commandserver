# This program may be used, executed, copied, modified and distributed
# without royalty for the purpose of developing, using, marketing, or distributing.
from java.lang import String
from java.util import Properties
from com.ibm.ws.scripting import ScriptingException
from java.io import File
from java.io import FileOutputStream
from java.io import FileInputStream
from java.io import PrintStream

#########################################################################################################
#
#  Verify input parameters
#
#########################################################################################################
# Usage method
def usage():
	print ""
	print "Usage to attach bindings:"
	print "wsadmin -lang jython -f bindings.py attach policySetName"
	print "Usage to remove bindings:"
	print "wsadmin -lang jython -f bindings.py remove policySetName"
	sys.exit()

# Init the global variables
action = ""
dosave = 1
ischanged = 0
resultOfCall = ""
alreadyattached = 0

# Get the action parameter
try:
	action = sys.argv[0]
except:
	print "You must specify an action, either 'attach' or 'remove'"
	usage()
if (action != "attach") and (action != "remove"):
	print "You must specify an action, either 'attach' or 'remove'"
	usage()

# Get cell type
cell=AdminConfig.list("Cell")
cellType=AdminConfig.showAttribute(cell, "cellType")

# Next get the policy set, node and cell
try:
	myPolicySet = sys.argv[1]
	wasNode = sys.argv[2]
	wasCell = sys.argv[3]
	wasServer = sys.argv[4]
except:
	print 'You must specify a policy set name, server node, server cell and server name.'
	usage()

#########################################################################################################
#
#  Policy set methods
#
#########################################################################################################

# A method to create a policy set attachment
def createPolicySetAttachment(appName, policySetName, attachmentType, resources):
	global resultOfCall, ischanged, dosave
	try:
		attrs = '-applicationName ' + appName + ' -attachmentType ' + attachmentType + ' -policySet "' + policySetName + '" -resources "' + resources + '"'
		resultOfCall = AdminTask.createPolicySetAttachment(attrs)
		print "Attached policy set '"+policySetName+"' to '"+appName+"' ID="+resultOfCall
		ischanged = 1
	except:
		dosave = 0
		print "ERROR: Exception creating policy set attachment " + policySetName

# A method to set the bindings on an attachment
def setBinding(policyTypeName, appName, psID, attachmentType, bindingName, expectedResult):
	global resultOfCall, ischanged, dosave
	try:
		attrs = '-policyType ' + policyTypeName + ' -bindingLocation "[ [application ' + appName + '] [attachmentId ' + psID + '] ]" -attachmentType ' + attachmentType + ' -bindingName ' + bindingName
		resultOfCall = AdminTask.setBinding(attrs)
		print "Attached binding '"+bindingName+"/"+policyTypeName+"' to '"+appName+"'"
		ischanged = 1
	except:
		dosave = 0
		print "ERROR: Exception setting binding " + bindingName 

# A method to delete a policy set attachment
def deletePolicySetAttachment(appName, polname, aType, allOthers):
	global dosave, ischanged, alreadyattached
	found = 0
	try:
		# List all policies attached to specified app
		policies = AdminTask.getPolicySetAttachments('[-applicationName '+appName+' -attachmentType '+aType+']')
		if len(policies) != 0:
			iLen = len(policies) - 1
			policy =  policies[1:iLen].splitlines()
			for pol in policy:
				# See if it matches 
				if ((allOthers == "y") & (pol.find(polname) != -1)):
					print "Policy set '"+polname+"' is already attached to '"+appName+"'"
					alreadyattached = 1
				# See if this one should be detached
				if (polname == "*") | ((allOthers == "y") & (pol.find(polname) == -1)) | ((allOthers == "n") & (pol.find(polname) != -1)):
					attrs = pol.split("[")
					for attr in attrs:
						# parse out the ID
						if attr.find("id") == 0:
							id1 = attr.split(" ")
							id2 = id1[1].split("]")
							# Perform the delete
							AdminTask.deletePolicySetAttachment('[-applicationName '+appName+' -attachmentId '+id2[0]+' -attachmentType '+aType+']')
							print "Deleted policy set ID="+ id2[0] + " from application '"+appName+"'"
							found = 1

		# Print a message if not found
		if 0 == found:
			if allOthers == "n":
				print "Cannot find '"+appName+"' attachment for '"+polname+"'"
		else:
			ischanged = 1

   	except:
		dosave = 0
   		print "ERROR: Exception removing policy set "+polname

# A method to force node sync
def forceSync():
    try:
		nodeSyncObjects = AdminControl.queryNames("type=NodeSync,*")
		if len(nodeSyncObjects) > 0:
			nodeSyncObjectList = nodeSyncObjects.split("\r\n")
			for nodeSync in nodeSyncObjectList:
				result = "false"
				print "nodeSync for " + nodeSync
				try:
					while result != "true":
						print "Force NodeSync ..."
						result = AdminControl.invoke(nodeSync, "sync", "")
						print "Sync result is " + result
				except:
					print "ERROR: AdminControl.invoke(" + nodeSync + ", 'sync', '') exception"
			result = "false"
			while result == "false":
				result = AdminApp.isAppReady('WSSampleClientSei')
				print "IsAppReady WSSampleClientSei="+result
			result = "false"
			while result == "false":
				result = AdminApp.isAppReady('WSSampleServicesSei')
				print "IsAppReady WSSampleServicesSei="+result
		else:
			print "Node Sync Not Applicable"
    except:
        print "ERROR: forceSync exception"
    return

# A method to save and restart
def restartApps ():
	global dosave, ischanged
	if (ischanged == 1) & (dosave == 1):
		try:
			# Save the configuration
			print "Saving configuration ..."
			AdminConfig.save()
			ischanged = 0
		except:
			print "ERROR: Exception saving configuration"

		# Try to sync
		forceSync()

		try:
			# Finally try to restart the applications
			# For standalone, we ignore the process name
			if (cellType == "STANDALONE"):
				print "Restarting applications in single server ..."
				appManagerObjects = AdminControl.queryNames('cell='+wasCell+',node='+wasNode+',type=ApplicationManager,*')
			else:
				print "Restarting applications in ND ..."
				appManagerObjects = AdminControl.queryNames('cell='+wasCell+',node='+wasNode+',process='+wasServer+',type=ApplicationManager,*')
			if len(appManagerObjects) > 0:
				appManagerList = appManagerObjects.split("\r\n")
				for appManager in appManagerList:
					AdminControl.invoke(appManager, 'stopApplication', 'WSSampleClientSei')
					AdminControl.invoke(appManager, 'stopApplication', 'WSSampleServicesSei')
					AdminControl.invoke(appManager, 'startApplication', 'WSSampleClientSei')
					AdminControl.invoke(appManager, 'startApplication', 'WSSampleServicesSei')
			else:
				print "ERROR: No ApplicationManager available"
				print "Make sure the cell, node, and server names are correct and the server is started."
		except:
			print "WARNING: Applications may not have restarted correctly."
			print "         Make sure the server is started and use the console to restart the samples."


#########################################################################################################
#
#  Perform the action
#
#########################################################################################################

##################
# Setup bindings #
##################
if action == "attach":

	# Remove any existing policy set attachments
	deletePolicySetAttachment("WSSampleServicesSei", myPolicySet, "application", "y")
	deletePolicySetAttachment("WSSampleClientSei", myPolicySet, "client", "y")

	if 0 == alreadyattached:

		### CREATE ATTACHMENTS ###
		try:
			# Create the policy set application attachment
			myApplication = "WSSampleServicesSei"
			createPolicySetAttachment(myApplication, myPolicySet, "application", "WebService:/")
			myAppPolicySetAttachmentID = resultOfCall
	
			# Set the bindings for RAMP default
			if myPolicySet == "RAMP default":
				# Set the binding for the application attachment
				setBinding("WSSecurity", myApplication, myAppPolicySetAttachmentID, "application", "RAMP_default_bindings", "true")
				setBinding("WSReliableMessaging", myApplication, myAppPolicySetAttachmentID, "application", "RAMP_default_bindings", "true")
	
			# Set the bindings for SecureConversation
			if myPolicySet == "SecureConversation":
				# Set the binding for the application attachment
				setBinding("WSSecurity", myApplication, myAppPolicySetAttachmentID, "application", "SecureConversation123binding", "true")
	
			# Create the policy set client attachment
			myApplication = "WSSampleClientSei"
			createPolicySetAttachment(myApplication, myPolicySet, "client", "WebService:/")
			myClientPolicySetAttachmentID = resultOfCall
	
			# Set the bindings for RAMP default
			if myPolicySet == "RAMP default":
				# Set the binding for the application attachment
				setBinding("WSSecurity", myApplication, myClientPolicySetAttachmentID, "client", "RAMP_client_default_bindings", "true")
				setBinding("WSReliableMessaging", myApplication, myClientPolicySetAttachmentID, "client", "RAMP_client_default_bindings", "true")
	
				# Set the bindings for SecureConversation
			if myPolicySet == "SecureConversation":
				setBinding("WSSecurity", myApplication, myClientPolicySetAttachmentID, "client", "SecureConversation123binding", "true")
	
		except:
			print "ERROR: Setup bindings failed with exception."
			dosave = 0


###################
# Remove bindings #
###################
if action == "remove":

	print "Removing policy set '"+myPolicySet+"' ..."

	# Delete the policy set application attachment
	deletePolicySetAttachment("WSSampleServicesSei", myPolicySet, "application", "n")

	# Delete the policy set client attachment
	deletePolicySetAttachment("WSSampleClientSei", myPolicySet, "client", "n")


#########################################################################################################
#
#  Finish
#
#########################################################################################################
# Save after the action is done
if 1 == dosave:
	restartApps()

else:
	AdminConfig.reset()
	print "ERROR:  "+action+" FAILED"
