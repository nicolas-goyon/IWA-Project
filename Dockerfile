FROM alpine
WORKDIR /app
COPY . .

# Install Docker Compose if it's not already installed
RUN apk add --no-cache docker-compose

# Define the command to run when the container starts
CMD cd /app/api_gateway && docker-compose up -d --build && cd /app/ms_user && docker-compose up -d --build

EXPOSE 8080
