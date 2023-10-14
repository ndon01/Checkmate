# ---- Base Node ----
FROM node:18 AS base
WORKDIR /app
COPY package*.json ./

# ---- Dependencies ----
FROM base AS dependencies
RUN npm set progress=false && npm config set depth 0
RUN npm install --only=production 
COPY . .

# ---- Test & Build ----
FROM dependencies AS build
RUN npm run build

# ---- Release ----
FROM nginx:alpine AS release
COPY --from=build /app/build /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
