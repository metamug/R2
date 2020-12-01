import ErrorModalProvider from 'providers/ErrorModalContext'
import React, { createContext, useContext, useEffect, useState } from 'react'
import LoadingProvider from 'providers/LoadingContext'
import { userLoggedIn } from 'utils/auth'

const GlobalContext = createContext()

const defaultAuthState = {
  token: '',
  isLoggedIn: false,
}

export default function GlobalProvider(props) {
  const [authState, setAuthState] = useState(defaultAuthState)

  const updateAuthState = () => {
    const loggedIn = userLoggedIn()
    if (loggedIn) {
      setAuthState({
        token: localStorage.getItem('token'),
        isLoggedIn: true,
      })
    } else {
      window.location.href = '/login'
    }
  }

  useEffect(() => {
    updateAuthState()
  }, [])

  return (
    <GlobalContext.Provider
      value={{
        globalState: {
          token: authState.token,
          isLoggedIn: authState.isLoggedIn,
        },
        globalHandlers: {
          updateAuthState,
        },
      }}
    >
      <ErrorModalProvider>
        <LoadingProvider>{props.children}</LoadingProvider>
      </ErrorModalProvider>
    </GlobalContext.Provider>
  )
}

function useGlobalContext() {
  const context = useContext(GlobalContext)
  if (context === undefined) {
    throw new Error('useCountDispatch must be used within a CountProvider')
  }
  return [context.globalState, context.globalHandlers]
}

export { GlobalContext, useGlobalContext }
