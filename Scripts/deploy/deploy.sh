#!/bin/bash

set -e

echo "=== Lumiticketing 배포 시작 ==="
echo "[1/2] Tomcat 재시작 중..."
sudo systemctl restart tomcat
echo "[2/2] 배포 완료 🎉"
