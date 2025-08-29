# Dockerfile para React frontend
FROM node:20-alpine
WORKDIR /app
COPY package.json package-lock.json ./
COPY src ./src
COPY public ./public
RUN npm install --legacy-peer-deps
EXPOSE 3000
CMD ["npm", "start"]
