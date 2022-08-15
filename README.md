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
docker run --rm -p 6969:6969 ghcr.io/artnuke/stupidgate:latest
```
