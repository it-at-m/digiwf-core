FROM nginxinc/nginx-unprivileged:1.25.3-alpine

# Copy nginx configuration
COPY docker/default.conf /etc/nginx/conf.d/default.conf

# Copy assets
COPY dist/src /usr/share/nginx/html/src
COPY dist/loader-*.js /usr/share/nginx/html

# Expose port
EXPOSE 8080

# Start nginx webserver
CMD ["nginx", "-g", "daemon off;"]
