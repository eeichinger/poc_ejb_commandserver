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
	print "Usage to install samples:"
	print "wsadmin -lang jython -f installapps.py install"
	print "Usage to uninstall samples:"
	print "wsadmin -lang jython -f installapps.py uninstall"
	sys.exit()

# Init the global variables
action = ""
ischanged = 0

# Get the action parameter
try:
	action = sys.argv[0]
except:
	print "You must specify an action, either 'install' or 'uninstall'"
	usage()
if (action != "install") and (action != "uninstall"):
	print "You must specify an action, either 'install' or 'uninstall'"
	usage()
        
# Get cell type
cell=AdminConfig.list("Cell")
cellType=AdminConfig.showAttribute(cell, "cellType")

# Next get the node and cell
try:
	wasNode = sys.argv[1]
	wasCell = sys.argv[2]
	wasServer = sys.argv[3]
except:
	print 'You must specify the server node, server cell and server name on which the sample applications will be installed'
	usage()

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

			if (action == "install"):                
				result = "false"
				while result == "false":
					result = AdminApp.isAppReady('WSSampleClientSei')
					print "IsAppReady WSSampleClientSei="+result
				result = "false"
				while result == "false":
					result = AdminApp.isAppReady('WSSampleServicesSei')
					print "IsAppReady WSSampleServicesSei="+result
				result = "false"
				while result == "false":
					result = AdminApp.isAppReady('WSSampleMTOMClient')
					print "IsAppReady WSSampleMTOMClient="+result
				result = "false"
				while result == "false":
					result = AdminApp.isAppReady('WSSampleMTOMService')
					print "IsAppReady WSSampleServicesSei="+result
		else:
			print "Node Sync Not Applicable"
    except:
        print "ERROR: forceSync exception"
    return

# A method to save and restart
def restartApps ():
	global ischanged
	if (ischanged == 1):
		try:
			# Save the configuration
			print "Saving configuration ..."
			AdminConfig.save()
			ischanged = 0
		except:
			print "ERROR: Exception saving configuration"

		# Try to sync
		forceSync()


		if action == "install":
			try:
				# Finally try to restart the applications
				# For standalone, we ignore the process name
				if (cellType == "STANDALONE"):
					print "Starting applications in single server ..."
					appManagerObjects = AdminControl.queryNames('cell='+wasCell+',node='+wasNode+',type=ApplicationManager,*')
				else:
					print "Starting applications in ND ..."
					appManagerObjects = AdminControl.queryNames('cell='+wasCell+',node='+wasNode+',process='+wasServer+',type=ApplicationManager,*')
				if len(appManagerObjects) > 0:
					appManagerList = appManagerObjects.split("\r\n")
					for appManager in appManagerList:
						AdminControl.invoke(appManager, 'startApplication', 'WSSampleClientSei')
						AdminControl.invoke(appManager, 'startApplication', 'WSSampleServicesSei')
						AdminControl.invoke(appManager, 'startApplication', 'WSSampleMTOMClient')
						AdminControl.invoke(appManager, 'startApplication', 'WSSampleMTOMService')
				else:
					print "ERROR: No ApplicationManager available for cell=" + wasCell + ", node=" + wasNode + ", server=" + wasServer
					print "Make sure the cell, node, and server names are correct and the server is started."
			except:
				print "WARNING: Applications may not have started correctly."
				print "         Make sure the server is started and use the console to restart the samples."


#########################################################################################################
#
#  Perform the action
#
#########################################################################################################

##################
# Install the Applications
# For ND, the target server (cell, node, and server names) must be specified. Otherwise, the applications will be installed on dmgr.
# For single server, cell, node, and server names are not required.
##################
if action == "install":

   	try:
		if (cellType == "STANDALONE"):
			AdminApp.install('WSSampleMTOMClient.ear', '[ -appname WSSampleMTOMClient ]' )
		else:
			AdminApp.install('WSSampleMTOMClient.ear', '[ -appname WSSampleMTOMClient -cell '+wasCell+' -node '+wasNode+' -server '+wasServer+' ]' )
   		ischanged = 1

   	except:
		print "WARNING: Cannot install WSSampleMTOMClient."

	try:
		if (cellType == "STANDALONE"):
			AdminApp.install('WSSampleMTOMService.ear', '[ -appname WSSampleMTOMService ]' )   
		else:
			AdminApp.install('WSSampleMTOMService.ear', '[ -appname WSSampleMTOMService  -cell '+wasCell+' -node '+wasNode+' -server '+wasServer+' ]' )
		ischanged = 1
	
	except:
		print 'WARNING: Cannot install WSSampleMTOMService.'

   	try:
		if (cellType == "STANDALONE"):
			AdminApp.install('WSSampleClientSei.ear', '[ -appname WSSampleClientSei ]' )
		else:
			AdminApp.install('WSSampleClientSei.ear', '[ -appname WSSampleClientSei -cell '+wasCell+' -node '+wasNode+' -server '+wasServer+' ]' )
   		ischanged = 1

   	except:
		print "WARNING: Cannot install WSSampleClientSei."

   	try:
		if (cellType == "STANDALONE"):
			AdminApp.install('WSSampleServicesSei.ear', '[ -appname WSSampleServicesSei ]' )
		else:
			AdminApp.install('WSSampleServicesSei.ear', '[ -appname WSSampleServicesSei  -cell '+wasCell+' -node '+wasNode+' -server '+wasServer+' ]' )
   		ischanged = 1

   	except:
		print "WARNING: Cannot install WSSampleServicesSei."



###################
# Uninstall the Applications
###################
if action == "uninstall":
	try:
		AdminApp.uninstall('WSSampleMTOMClient' )
		ischanged = 1
	
	except:
		print "WARNING: Cannot uninstall WSSampleMTOMClient."

   	try:
   		AdminApp.uninstall('WSSampleMTOMService' )
   		ischanged = 1

   	except:
   		print "WARNING: Cannot uninstall WSSampleMTOMService."

  	try:
  		AdminApp.uninstall('WSSampleClientSei' )
  		ischanged = 1

  	except:
  		print "WARNING: Cannot uninstall WSSampleClientSei."


   	try:
   		AdminApp.uninstall('WSSampleServicesSei' )
   		ischanged = 1

   	except:
   		print "WARNING: Cannot uninstall WSSampleServicesSei."

#########################################################################################################
#
#  Finish
#
#########################################################################################################
# Save after the action is done
if 1 == ischanged:
	restartApps()

else:
	AdminConfig.reset()
	print "WARNING:  "+action+" FAILED"
