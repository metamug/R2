import NavigationBar from 'app/NavigationBar'
import Resources from 'features/resources/Resources'
import ResourceEditor from 'features/xml-editor/ResourceEditor'
import ErrorModalProvider from 'providers/ErrorModalContext'
import LoadingProvider from 'providers/LoadingContext'
import React, { useEffect } from 'react'
import Switch from 'react-bootstrap/esm/Switch'
import { Redirect, Route } from 'react-router-dom'
import APIDocs from '../features/docs/Redoc.jsx'

const RedirectToResources = () => <Redirect to="/resources" />

function App() {
  return (
    <div className="App">
      <LoadingProvider>
        <ErrorModalProvider>
          <NavigationBar />
          <Switch>
            {/*<Redirect exact from="/" to="/resources" />*/}
            <Route exact path="/" component={RedirectToResources} />
            <Route exact path="/resources" component={Resources} />
            <Route exact path="/editor" component={ResourceEditor} />
            <Route exact path="/docs/:app" component={APIDocs} />
          </Switch>
        </ErrorModalProvider>
      </LoadingProvider>
    </div>
  )
}

export default App
