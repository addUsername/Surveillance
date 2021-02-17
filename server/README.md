Server project, its principal mission is to proxy Pi(s) and Client projects. Each user will operate its own server which, also, have to be deployed by himself.

## Endpoints

`/auth` : not jwt token needed
- /register: Server has an in memory database, an encrypted dump.sql is generated and sended to user if succesfull sign in.
- /login: Recives that dump.sql and a pin, do its own thing and a jwt token is returned.

`/media` : Auth header needed
- /config/{idPiCamera}: RPi's settings, customize metadata, mode, quality, etc.. This rest endpoint sends via Websocket the settings.properties file generated to RPi
- /stream/{idPiCamera}: streams as .mjpeg or .hs264, it could stream multiple RPis at once.. i hope.
- /home:
- /generalView: appends selected RPis streams to the same frame and generate a mjpeg stream.

## Wishlist
- Basic auth & security
- change ws protocols that allow working with binary data directly (no b64)
- Pi configurer
- divide in restService + auth (server-user), wsService (server-pi), ?? apps
