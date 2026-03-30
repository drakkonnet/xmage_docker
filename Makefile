-include .env

# The target directory is used for setting where the output zip files will end up
# You can override this with an environment variable, ex
# TARGET_DIR=my_custom_directory make deploy
# Alternatively, you can set this variable in the .env file
TARGET_DIR ?= deploy/

.PHONY: clean
clean:
	mvn clean

.PHONY: build
build:
	mvn install package -DskipTests

.PHONY: package
package:
	# Packaging Mage.Client to zip
	cd Mage.Client && mvn package assembly:single
	# Copying the client file to the target directory
	mkdir -p $(TARGET_DIR)
	cp ./Mage.Client/target/mage-client.zip $(TARGET_DIR)

# Docker targets
.PHONY: docker-build
docker-build:
	docker build -f Dockerfile.xmage-build -t xmage-build .

.PHONY: docker-server-build
docker-server-build:
	docker build -f Dockerfile.xmage-server -t xmage-server .

.PHONY: docker-run
docker-run:
	docker run -p 17171:17171 -p 17179:17179 xmage-server

# Client build and run targets (for local execution)
.PHONY: build-client
build-client:
	cd Mage.Client && mvn package assembly:single

.PHONY: run-client
run-client: build-client
	mkdir -p temp && cp Mage.Client/target/mage-client.zip temp/client.zip && \
	cd temp && unzip -q client.zip -d client && \
	cd client && java -jar lib/mage-client-1.4.58.jar

.PHONY: clean-client
clean-client:
	rm -rf temp

# Note that the proper install script is located under ./Utils/build-and-package.pl
# and that should be used instead. This script is purely for convenience.
# The perl script bundles the artifacts into a single zip
.PHONY: install
install: clean build package

