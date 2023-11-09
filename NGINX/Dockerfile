# Use the official Nginx image as a parent image
FROM nginx:latest

# Set working directory to nginx asset directory
WORKDIR /etc/nginx

# Remove the default config file and add our custom Nginx config
RUN rm /etc/nginx/conf.d/default.conf
COPY nginx.conf /etc/nginx/conf.d/

# Copy over SSL certificates
# Make sure to create the certs directory if it does not already exist
COPY localhost.crt /etc/nginx/certs/
COPY localhost.key /etc/nginx/certs/

# Expose ports
EXPOSE 80
EXPOSE 443

# Start Nginx when the container has provisioned
CMD ["nginx", "-g", "daemon off;"]
