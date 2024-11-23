# Image names are in the format ghcr.io/${{ github.repository_owner }}/${{ env.MICROSERVICE_NAME }}-app:latest
# The database image name is in the format ghcr.io/${{ github.repository_owner }}/${{ env.MICROSERVICE_NAME }}-db:latest

# Microservices names
MS_USER=ms_users
MS_RESERVATION=ms_reservation
MS_LOCATION=ms_location
MS_NOTIFICATION=ms_notification

# API Gateway
API_GATEWAY=api_gateway

# Repository owner from arguments
REPOSITORY_OWNER=$1

# Login token from arguments
LOGIN_TOKEN=$2

# Check arguments
if [ -z "$REPOSITORY_OWNER" ] || [ -z "$LOGIN_TOKEN" ]; then
  echo "Usage: deploy.sh <repository_owner> <login_token>"
  exit 1
fi

# Login to GitHub Container Registry
echo $LOGIN_TOKEN | docker login ghcr.io -u $REPOSITORY_OWNER --password-stdin
echo "Logged in to GitHub Container Registry"

# Deployment order
echo "Deploying microservices..."

# Deploy User microservice
docker pull ghcr.io/$REPOSITORY_OWNER/$MS_USER-app:latest
docker pull ghcr.io/$REPOSITORY_OWNER/$MS_USER-db:latest

