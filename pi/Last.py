from base64 import b64encode
import stomper
import requests
import time
import websocket
import threading

def on_message(ws, message):

    print('### message ###')
    print('<< ' + message)
    ws.send(stomper.send("/app/mjpeg/2", b64encode("hola".encode("utf-8")).decode("utf-8")))
    
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
def run():
    while True:
        print("ee")
        time.sleep(2)
    

if __name__ == '__main__':
      
    websocket.enableTrace(True)

    connection_url = "ws://192.168.1.51:8080/gs-guide-websocket"
    CONNECT = "CONNECT\naccept-version:1.0,1.1,2.0\n\n\x00\n"

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