// Wraps Entire app, check entry.js
import React, { createContext, useContext, useReducer } from 'react'
import ErrorModal from 'components/ErrorModal'

const ErrorStateContext = createContext()
const ErrorDispatchContext = createContext()

const initialModalState = {
  open: false,
  message: '',
}

function openModal(state, payload) {
  const { message } = payload
  return { open: true, message }
}

const actionTypes = {
  OPEN: 'open',
  CLOSE: 'close',
}

function reducer(state, action) {
  const { payload } = action
  switch (action.type) {
    case actionTypes.OPEN:
      return openModal(state, payload)
    case actionTypes.CLOSE:
      return { open: false, message: '' }
    default:
      throw new Error('Invalid Action')
  }
}

export default function ErrorModalProvider(props) {
  const [state, dispatch] = useReducer(reducer, initialModalState)

  return (
    <ErrorDispatchContext.Provider value={dispatch}>
      <ErrorStateContext.Provider value={state}>
        <ErrorModal state={state} dispatch={dispatch} />
        {props.children}
      </ErrorStateContext.Provider>
    </ErrorDispatchContext.Provider>
  )
}

function useErrorModalContext() {
  const state = useContext(ErrorStateContext)
  const dispatch = useContext(ErrorDispatchContext)
  if (state === undefined || dispatch === undefined) {
    throw new Error('no context available')
  }
  return [state, dispatch]
}

const withErrorModalContext = (Component) => (props) => {
  const [state, dispatch] = useErrorModalContext()
  return <Component dispatch={dispatch} state={state} {...props} />
}

export { useErrorModalContext, withErrorModalContext }
