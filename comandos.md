Teniendo el package.json
```
{
  "name": "server",
  "version": "1.0.0",
  "description": "",
  "main": "index.js",
  "scripts": {
    "build": "tsc -w",
    "dev": "nodemon build/index.js"
  },
  "keywords": [],
  "author": "",
  "license": "ISC",
  "dependencies": {
    "cors": "^2.8.5",
    "express": "^4.17.1",
    "morgan": "^1.10.0",
    "promise-mysql": "^3.3.1"
  },
  "devDependencies": {
    "@types/cors": "^2.8.7",
    "@types/express": "^4.17.8",
    "@types/morgan": "^1.9.1",
    "nodemon": "^2.0.4"
  }
}
```

Iniciar la compilacion en modo vigilante
```
npm run build
```

Ejecutar cambios
```
npm run dev
```
