clamav_version=1.1.3-35
container_name=clamav

start_clamav:
	docker run \
		--detach \
		--publish 3310:3310 \
		--rm \
		--name $(container_name) \
		docker.io/clamav/clamav:$(clamav_version)
	docker logs --follow $(container_name)

stop_clamav:
	docker kill $(container_name)