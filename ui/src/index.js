import React from 'react'
import ReactDOM from 'react-dom'
import 'assets/vendor/css/bootstrap.min.css'
import 'assets/vendor/css/font-awesome.min.css'
import './index.css'
import App from './app/App'
import { BrowserRouter as Router } from 'react-router-dom'
import GlobalProvider from './providers/GlobalContext'

ReactDOM.render(
  <React.StrictMode>
    <Router>
      <GlobalProvider>
        <App />
      </GlobalProvider>
    </Router>
  </React.StrictMode>,
  document.getElementById('root')
)
