# XMage Docker

This repository contains Docker configurations for building and running XMage.

## Changes Made

- `Dockerfile.xmage-build`: Builds the XMage server (excludes client and related modules).
- `Dockerfile.xmage-server`: Runs the XMage server using the build image.
- `docker-compose.yml`: Orchestrates the server container.
- `Makefile`: Updated with Docker build/run targets and client build/run targets.

For full XMage documentation, see the [original XMage repository](https://github.com/magefree/mage).


### Building from source

#### Prerequisites
- Java 11+ (for client build) or Java 8+ (for server only)
- Maven
- Docker (optional, for containerized builds)

#### Building the Server (Docker)
1. Build the base image: `make docker-build`
2. Build the server image: `make docker-server-build`
3. Run the server: `make docker-run`

Or use Docker Compose: `docker-compose up`

#### Building the Client (Local)
1. Ensure Java 11+ is installed.
2. Build the client: `make build-client`
3. Run the client: `make run-client`

#### Building Both (Local)
- `make install` - Builds and packages the client locally.
- For server, use Docker as above.
