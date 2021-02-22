from base64 import b64encode, decode
import stomper
import requests
import time
import websocket
import threading
import asyncio

import json

'''
# Constants Copied from AppSync API 'Settings'
API_URL = <Paste API URL Here>
API_KEY = <Paste Key Here>

# GraphQL subscription Registration object
GQL_SUBSCRIPTION = json.dumps({
        'query': 'subscription OnUpdateTodo { onUpdateTodo { __typename id title } }',
        'variables': {}
})

# Discovered values from the AppSync endpoint (API_URL)
WSS_URL = API_URL.replace('https','wss').replace('appsync-api','appsync-realtime-api')
HOST = API_URL.replace('https://','').replace('/graphql','')

# Subscription ID (client generated)
SUB_ID = str(uuid4())

# Set up Timeout Globals
timeout_timer = None
timeout_interval = 10

# Calculate UTC time in ISO format (AWS Friendly): YYYY-MM-DDTHH:mm:ssZ
def header_time():
    return datetime.utcnow().isoformat(sep='T',timespec='seconds') + 'Z'

# Encode Using Base 64
def header_encode( header_obj ):
    return b64encode(json.dumps(header_obj).encode('utf-8')).decode('utf-8')

# reset the keep alive timeout daemon thread
def reset_timer( ws ):
    global timeout_timer
    global timeout_interval

    if (timeout_timer):
        timeout_timer.cancel()
    timeout_timer = threading.Timer( timeout_interval, lambda: ws.close() )
    timeout_timer.daemon = True
    timeout_timer.start()

# Create API key authentication header
api_header = {
    'host':HOST,
    'x-api-key':API_KEY
}'''

# Socket Event Callbacks, used in WebSocketApp Constructor
def on_message(ws, message):

    print('### message ###')
    print('<< ' + message)
    ws.send(stomper.send("/app/mjpeg/2", b64encode("hola".encode("utf-8")).decode("utf-8")))

    '''
    message_object = json.loads(message)
    message_type   = message_object['type']
    print(message_type)
    
    if( message_type == 'ka' ):
        print("ka")
        #reset_timer(ws)

    elif( message_type == 'connection_ack' ):
       timeout_interval = int(json.dumps(message_object['payload']['connectionTimeoutMs']))

        register = {
            'id': SUB_ID,
            'payload': {
                'data': GQL_SUBSCRIPTION,
                'extensions': {
                    'authorization': {
                        'host':HOST,
                        'x-api-key':API_KEY
                    }
                }
            },
            'type': 'start'
        }
        start_sub = json.dumps(register)
        print('>> '+ start_sub )
        ws.send(start_sub)

    elif(message_type == 'data'):
        deregister = {
            'type': 'stop',
            'id': SUB_ID
        }
        end_sub = json.dumps(deregister)
        print('>> ' + end_sub )
        ws.send(end_sub)

    elif(message_object['type'] == 'error'):
        print ('Error from AppSync: ' + message_object['payload'])
    '''
    
def on_error(ws, error):
    print('### error ###')
    print(error)

def on_close(ws):
    print('### closed ###')

def on_open(ws):
    print('### opened ###')
    ws.send(CONNECT)
    y = stomper.subscribe("/topic/info/","2",ack="client")
    ws.send(y)
    '''
    init = {
        'type': 'connection_init'
    }
    init_conn = json.dumps(init)
    print('>> '+ init_conn)
    ws.send(init_conn)
    '''
def run():
    while True:
        print("ee")
        time.sleep(2)
    

if __name__ == '__main__':
      
    # Uncomment to see socket bytestreams
    websocket.enableTrace(True)

    # Set up the connection URL, which includes the Authentication Header
    #   and a payload of '{}'.  All info is base 64 encoded
    connection_url = "ws://192.168.1.51:8080/gs-guide-websocket"
    CONNECT = "CONNECT\naccept-version:1.0,1.1,2.0\n\n\x00\n"

    # MAKE IT POST METHOD AND RETRIEVE JWT OR NOT
    r = requests.get("http://192.168.1.51:8080/pi/ini/2")
    
    print( 'Connecting to: ' + connection_url )

    ws = websocket.WebSocketApp( connection_url,
                            on_open = on_open,
                            on_message = on_message,
                            on_error = on_error,
                            on_close = on_close,)
    
    wst = threading.Thread(target=ws.run_forever)
    wst.daemon = True
    wst.start()
    run()
    
    print("finish??")