#!/bin/sh

set -eu

CONFIG_PATH="${XMAGE_CONFIG_PATH:-/opt/xmage-server/config/config.xml}"
JAR_PATH="${XMAGE_SERVER_JAR:-/opt/xmage-server/lib/mage-server-1.4.58.jar}"
SERVER_ADDRESS="${XMAGE_SERVER_ADDRESS:-0.0.0.0}"
SERVER_NAME="${XMAGE_SERVER_NAME:-mage-server}"
SERVER_PORT="${XMAGE_SERVER_PORT:-17171}"
SECONDARY_BIND_PORT="${XMAGE_SECONDARY_BIND_PORT:-17179}"
BRIDGE_URL="${XMAGE_OLLAMA_BRIDGE_URL:-http://xmage-ollama-bridge:8787}"
CONNECT_TIMEOUT_MS="${XMAGE_OLLAMA_CONNECT_TIMEOUT_MS:-1500}"
READ_TIMEOUT_MS="${XMAGE_OLLAMA_READ_TIMEOUT_MS:-2500}"
STRATEGY="${XMAGE_OLLAMA_STRATEGY:-aggro-burn}"
JAVA_OPTS_VALUE="${XMAGE_JAVA_OPTS:--Xmx1024m}"

sed -i \
  -e "s/serverAddress=\"[^\"]*\"/serverAddress=\"$SERVER_ADDRESS\"/" \
  -e "s/serverName=\"[^\"]*\"/serverName=\"$SERVER_NAME\"/" \
  -e "s/port=\"[0-9-][0-9-]*\"/port=\"$SERVER_PORT\"/" \
  -e "s/secondaryBindPort=\"[0-9-][0-9-]*\"/secondaryBindPort=\"$SECONDARY_BIND_PORT\"/" \
  "$CONFIG_PATH"

echo "Starting XMage server on ${SERVER_ADDRESS}:${SERVER_PORT} (secondary ${SECONDARY_BIND_PORT}) using bridge ${BRIDGE_URL}"

# shellcheck disable=SC2086
exec java \
  $JAVA_OPTS_VALUE \
  "-Dxmage.config.path=${CONFIG_PATH}" \
  "-DXMAGE_OLLAMA_BRIDGE_URL=${BRIDGE_URL}" \
  "-DXMAGE_OLLAMA_CONNECT_TIMEOUT_MS=${CONNECT_TIMEOUT_MS}" \
  "-DXMAGE_OLLAMA_READ_TIMEOUT_MS=${READ_TIMEOUT_MS}" \
  "-DXMAGE_OLLAMA_STRATEGY=${STRATEGY}" \
  -jar "${JAR_PATH}"
