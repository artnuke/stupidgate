# stupidgate
## Installing Guid.
1) Run Redis DB in Docker Container.
```
docker run --name redis -p 6379:6379 redis 
```
2) Pull Docker Image from GitHub Registry. 
```
docker pull ghcr.io/artnuke/stupidgate:latest
```
3) Run StupidGate Container.
```
docker container run \
-p 6969:6969 \
--env spring.redis.host=<REDIS HOSTNAME> \
--env spring.redis.port=<REDIS PORT> \
ghcr.io/artnuke/stupidgate

```
