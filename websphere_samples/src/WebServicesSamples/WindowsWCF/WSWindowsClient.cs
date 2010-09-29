/** 
 * This program may be used, executed, copied, modified and distributed
 * without royalty for the purpose of developing, using, marketing, or distributing.
 * 
 **/

using System;
using System.Threading;

public partial class WSWindowsClient
{
    enum SERVTYPE { ECHO, PING, ASYNC }
    static string urlHost = "localhost";
    static string urlPort = "9080";
    static string ECHO_CONTEXT = "/WSSampleSei/EchoService";
    static string PING_CONTEXT = "/WSSampleSei/PingService";
    string urlSuffix = "";
    string uriString = "http://" + urlHost + ":" + urlPort;
    string msg = "HELLO";
    SERVTYPE servtype = SERVTYPE.ECHO;
    int timeout = 120;
    int SLEEPER = 10;

    /**
     * Main
     * main program entry point
     * 
     * @param args - string[]
     */
    static void Main(string[] args)
    {
        WSWindowsClient ws = new WSWindowsClient();
        if (ws.ParseArgs(args))
        {
            ws.PrintUsage();
            return;
        }
        else
        {
            ws.CallService();
        }
    }

    /**
     * ParseArgs
     * Read the program options from the command line
     * 
     * @param args - string[] - Same as main
     * @return - bool - true if there is an error that requires help
     */
    bool ParseArgs(string[] args)
    {
        bool isError = false;
        if (args.Length > 0)
        {

            for (int argc = 0; argc < args.Length; argc++)
            {
                try
                {
                    if (args[argc][0] == '-') // Options require '-'
                    {
                        switch (args[argc][1])
                        {
                            case 'm':
                            case 'M':
                                msg = args[++argc];
                                break;
                            case 'h':
                            case 'H':
                                urlHost = args[++argc];
                                break;
                            case 'p':
                            case 'P':
                                urlPort = args[++argc];
                                break;
                            case 'f':
                            case 'F':
                                urlSuffix = args[++argc];
                                break;
                            case 's':
                            case 'S':
                                if (args[++argc].Equals("ping"))
                                {
                                    servtype = SERVTYPE.PING;                             
                                }
                                else if (args[argc].Equals("echo"))
                                {
                                    servtype = SERVTYPE.ECHO;                                    
                                }
                                else if (args[argc].Equals("async"))
                                {
                                    servtype = SERVTYPE.ASYNC;                                  
                                }
                                else
                                {
                                    isError = true;
                                    Console.WriteLine("Unknown Service: " + args[argc]);
                                }
                                break;
                            case 't':
                            case 'T':
                                timeout = int.Parse(args[++argc]);
                                break;
                            case '?':
                                isError = true;
                                break;
                            default:
                                isError = true;
                                Console.WriteLine("Unknown Option: " + args[argc]);
                                break;
                        }
                    }
                    else
                    { // No '-'. 
                        isError = true;
                        Console.WriteLine("Unrecognized Parameter: " + args[argc]);
                    }
                }
                catch
                {  // Could be a missing option value
                    isError = true;
                    Console.WriteLine("Incorrect Parameter Format");
                    break; // the For loop
                }

            }
            uriString = "http://" + urlHost + ":" + urlPort;
        }
        return (isError);
    }

    /**
     * Printusage
     * Simple help for usage
     * 
     */
    void PrintUsage()
    {
        Console.WriteLine("Usage:");
        Console.WriteLine("  WSWindowsClient -h [hostname] -p [port] -f [urlSuffix] -m [testMessage] -s [echo|ping|async] -t [asynctimeout]");
        Console.WriteLine("Default values:");
        Console.WriteLine("  hostname= \"localhost\"");
        Console.WriteLine("  port= 9080");
        Console.WriteLine("  urlSuffix= \"/WSSampleSei/EchoService\"");
        Console.WriteLine("  testMessage= \"hello\"");
        Console.WriteLine("  service= echo");
        Console.WriteLine("  asynctimeout= 120");
    }

    /**
     * CallService
     * Parms were already read. Now call the service proxy classes
     * 
     */
    void CallService()
    {
        if (SERVTYPE.ECHO == servtype) // Echo 
        {
            if (0 == urlSuffix.Length)
            {
                urlSuffix = ECHO_CONTEXT;
            }
            // Instantiate the service, and create the service url 
            EchoServicePortTypeClient echosv =
              new EchoServicePortTypeClient("EchoServicePort", new System.ServiceModel.EndpointAddress(uriString + urlSuffix));
            Console.WriteLine("CLIENT>> Connecting to Echo Service...");
            try
            {
                // Sync invoke the service
                echoStringInput echoParm = new echoStringInput();
                echoParm.echoInput = msg;
                Console.WriteLine("CLIENT>> Sending message '" + msg + "' ...");
                echoStringResponse result = echosv.echoOperation(echoParm);
                Console.WriteLine("CLIENT>> The answer is '" + result.echoResponse + "'");
            }
            catch (Exception e)
            {
                Console.WriteLine(">>>ECHO SERVICE EXCEPTION<<<\n" + e);
            }
        }
        else if (SERVTYPE.ASYNC == servtype) // Async Echo 
        {
            if (0 == urlSuffix.Length)
            {
                urlSuffix = ECHO_CONTEXT;
            }
            // Instantiate the service, and create the service url 
            EchoServicePortTypeClient echosv =
              new EchoServicePortTypeClient("EchoServicePort", new System.ServiceModel.EndpointAddress(uriString + urlSuffix));
            Console.WriteLine("CLIENT>> Connecting to Async Echo Service...");
            try
            {
                // Create the argument object and IAsyncResult, then invoke Async
                echoStringInput echoParm = new echoStringInput();
                echoParm.echoInput = msg;
                Console.WriteLine("CLIENT>> Sending Async message '" + msg + "' ...");
                IAsyncResult ar = echosv.BeginechoOperation(echoParm, EchoCallback, echosv);
                int waiting = timeout;

                Thread.Sleep(1000);
                // Wait for completion
                while (!ar.IsCompleted)
                {
                    if (waiting <= 0)
                    {
                        Console.WriteLine("CLIENT>> ERROR - Timeout waiting for reply.");
                        break;
                    }
                    Console.WriteLine("CLIENT>> invocation still not complete");
                    Thread.Sleep(1000 * SLEEPER);
                    waiting -= SLEEPER;
                }
                Console.WriteLine("CLIENT>> Async Invocation Complete");
            }
            catch (Exception e)
            {
                Console.WriteLine(">>>ECHO SERVICE EXCEPTION<<<\n" + e);
            }
        }
        else  // must be Ping
        {
            if (0 == urlSuffix.Length)
            {
                urlSuffix = PING_CONTEXT;
            }
            // Instantiate the service, and create the service url 
            PingServicePortTypeClient pingsv =
              new PingServicePortTypeClient("PingServicePort", new System.ServiceModel.EndpointAddress(uriString + urlSuffix));
            Console.WriteLine("CLIENT>> Connecting to Ping Service...");
            try
            {
                // Create the argument object and Sync invoke the service
                pingStringInput pingParm = new pingStringInput();
                pingParm.pingInput = msg;
                Console.WriteLine("CLIENT>> Sending message '" + msg + "' ...");
                pingsv.pingOperation(pingParm);
                Console.WriteLine("CLIENT>> Ping complete. Check server log to verify message delivery.");
            }
            catch (Exception e)
            {
                Console.WriteLine(">>>PING SERVICE EXCEPTION<<<\n" + e);
            }
        }
    }

    /**
     * EchoCallback
     * Callback function to implement the Async call
     * 
     */
    static void EchoCallback(IAsyncResult ar)
    {
        // Get the service object from the IAsyncResult object and get the response
        EchoServicePortTypeClient echosv = (EchoServicePortTypeClient)ar.AsyncState;
        echoStringResponse result = echosv.EndechoOperation(ar);
        Console.WriteLine("CLIENT>> The async answer is '" + result.echoResponse + "'");
    }

} /* end module */
