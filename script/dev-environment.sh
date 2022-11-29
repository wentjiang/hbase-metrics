#!/bin/bash
set -eufo pipefail

cd "$(dirname "$0")/.."

docker-compose run --service-ports hbase "$@"