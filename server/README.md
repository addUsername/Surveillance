Server project, its principal mission is to proxy Pi(s) and Client projects. Each user will operate its own server which, also, have to be deployed by himself.

## Endpoints

`/auth` : not jwt token needed
- **/register:** Server has an in memory database, an encrypted dump.sql is generated and sended to user if succesfull sign in.
- **/login:** Receives that dump.sql and a pin, do its own thing and a jwt token is returned.
- **/dump:** Returns encrypted dump.sql
- 
`/user` : Auth header needed
- **/home:** ?? shows RPi's current status
- **/generalView:** appends selected RPis streams to the same frame and generate a mjpeg stream.
- **/setRpi:** Add a newRPi.
- **/config/{idPiCamera}:** RPi's settings, customize metadata, mode, quality, etc..
- **/stream/{idPiCamera}:** streams as .mjpeg or .hs264, it could stream multiple RPis at once.. i hope.
- **/ini/{idPiCamera}:** Pi visits this on startup, to get server status and settings.properties, for RPis the response code acts like a flag, tryig to connect till 200 ok are received

`/websocket` : auth needed?
-  **/stream/{idPiCamera}:** where RPi's websocket client connects and send video.
-  **/topic/info:** where all RPis subscribe and listen mssg from server. ??Prob not needed
- **{id}/queue/:** subscribe endpoint where send mssg to specifc RPi

## Wishlist
- Basic auth & security
- change ws protocols that allow working with binary data directly (no b64)
- Pi configurer
- divide in restService + auth (server-user), wsService (server-pi), ?? apps
