# This program may be used, executed, copied, modified and distributed
# without royalty for the purpose of developing, using, marketing, or distribution

#------------------------------------------------------------------------
#
# installEJB3CounterSample 
#
# This is a jython script designed to be run with the wsadmin command. It
# assumes a simple single node, single server install. It should be 
# invoked from within the <was-home>/bin directory as:
#      wsadmin.bat|sh -lang jython -f <path-to>/installEJB3CounterSample.py
#------------------------------------------------------------------------

# If the application is currently installed, uninstall it
try:
	AdminApp.uninstall('EJB3CounterSample')
except:
	print 'EJB3CounterSample was not previously installed'
AdminConfig.save()

# Set the virtual host for the web application
modtovh1 = ["Web Application", 'ejb3sample/counter.war,WEB-INF/web.xml', 'default_host']
modtovh = [modtovh1]

# Set the options for the install
attrs = ['-MapWebModToVH', modtovh, '-appname', 'EJB3CounterSample']

# Install the application
AdminApp.install('../samples/lib/EJB3SampleApplications/EJB3CounterSample.ear', attrs)
AdminConfig.save()

# Start the application
appManager = AdminControl.queryNames('type=ApplicationManager,*')
AdminControl.invoke(appManager, 'startApplication', 'EJB3CounterSample')
