import NavigationBar from 'app/NavigationBar'
import Resources from 'features/resources/Resources'
import ResourceManagement from 'features/resource-management/ResourceManagementContainer'
import ResourceEditor from 'features/xml-editor/ResourceEditor'
import ErrorModalProvider from 'providers/ErrorModalContext'
import LoadingProvider from 'providers/LoadingContext'
import React, { useEffect } from 'react'
import Switch from 'react-bootstrap/esm/Switch'
import { Redirect, Route } from 'react-router-dom'
import APIDocs from 'features/docs/Redoc.jsx'
import LoginView from 'features/auth/LoginView.jsx'
import ResourceProvider from '../providers/ResourceContext.jsx'
import { useGlobalContext } from '../providers/GlobalContext'

const RedirectToResources = () => <Redirect to="/resources" />

const LogOut = () => {
  useEffect(() => {
    localStorage.clear()
  }, [])
  return <Redirect to="/login" />
}

function App(props) {
  const [globalState] = useGlobalContext()
  useEffect(() => {
    console.log(globalState)
  }, [])
  return (
    <div className="App">
      <ResourceProvider>
        <LoadingProvider>
          <ErrorModalProvider>
            <NavigationBar />
            <Switch>
              {/*{!loggedIn && <Redirect exact to="/login" />}*/}
              <Route exact path="/" component={RedirectToResources} />
              <Route exact path="/login" component={LoginView} />
              <Route exact path="/logout" component={LogOut} />
              <Route exact path="/resources" component={Resources} />
              <Route exact path="/editor" component={ResourceEditor} />
              <Route exact path="/docs/:app" component={APIDocs} />
              <Route
                exact
                path="/resource-management"
                component={ResourceManagement}
              />
            </Switch>
          </ErrorModalProvider>
        </LoadingProvider>
      </ResourceProvider>
    </div>
  )
}

export default App
