#### Docker commands/scripts

- **Delete all images**  docker rmi -f $(docker images -q | grep dbosample) 

Login to container:
$ docker exec -ti b81c113178ae /bin/sh
# ls -la
total 76

# Delete all containers
docker rm $(docker ps -a -q)

# Stop all containers
docker stop $(docker ps -a -q)

# Delete all images
docker rmi $(docker images -q)




