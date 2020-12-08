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

const AuthenticatedRoute = (props) => {
  const { component: Component, auth, ...rest } = props
  debugger
  return (
    <Route
      {...rest}
      render={(props) =>
        auth === true ? <Component {...props} /> : <Redirect to="/login" />
      }
    />
  )
}

const RedirectToResources = () => <Redirect to="/resources" />

const LogOut = () => {
  useEffect(() => {
    localStorage.clear()
  }, [])
  return <Redirect to="/login" />
}

function App(props) {
  const [globalState] = useGlobalContext()
  const { isLoggedIn } = globalState
  return (
    <div className="App">
      <LoadingProvider>
        <ErrorModalProvider>
          <NavigationBar />
          <Switch>
            {/*{!loggedIn && <Redirect exact to="/login" />}*/}
            <Route exact path="/" component={RedirectToResources} />
            <Route exact path="/login" component={LoginView} />
            <AuthenticatedRoute
              exact
              path="/logout"
              auth={isLoggedIn}
              component={LogOut}
            />
            <AuthenticatedRoute
              exact
              path="/resources"
              auth={isLoggedIn}
              component={Resources}
            />
            <AuthenticatedRoute
              exact
              path="/editor"
              auth={isLoggedIn}
              component={ResourceEditor}
            />
            <AuthenticatedRoute exact path="/docs/:app" component={APIDocs} />
            <Route
                exact
                path="/resource-management"
                component={ResourceManagement}
              />
          </Switch>
        </ErrorModalProvider>
      </LoadingProvider>
    </div>
  )
}

export default App
