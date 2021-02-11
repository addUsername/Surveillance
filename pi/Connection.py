import request

class Connection:
    
    pi_id = "1"
    hello = "/ini"
    video_type = "/mjpg"
    
    CONNECT = "CONNECT\naccept-version:1.0,1.1,2.0\n\n\x00\n"
    send_path = "/app/mjpg"
    sub_path =  "/topic/info"
    
    def __init__(self, host, pi_id):
        self.host = host
        #desde fuera
        #self.token = requests.get("/ini/1");
        self.connect()
    
    def connect(self):
        self.ws = create_connection("ws://"+self.host+"/"+self.video_type, timeout=5)
        self.ws.send(CONNECT)
        sub = stomper.subscribe(self.sub_path,self.pi_id,ack="auto")
        self.ws.send(sub)
        print(self.ws.recv())
    
    async def write(self,buf):
        # add id here?
        message = stomper.send(self.send_path +"/"+self.pi_id, b64encode(buf).decode("utf-8"))
        try:
            self.ws.send(message)
            #self.ws.recv()
        except:
            self.connect()
            #self.write(buf)
            self.ws.send(message)
        print("send")
    
    async def listen(self):
        return self.ws.recv()
    
    def close(self):
        self.ws.close()
        print("closed")
        