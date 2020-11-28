// Wraps Entire app, check entry.js
import React, { createContext, useContext, useReducer } from 'react'
import LoadingModal from 'components/LoadingModal'

const LoadingStateContext = createContext()
const LoadingDispatchContext = createContext()

const defaultLoadingMessage = 'Loading ...'

const initialModalState = {
  loading: false,
  message: defaultLoadingMessage,
}

function openLoadingModal(state, payload) {
  const { message } = payload || {}
  return { loading: true, message: message || defaultLoadingMessage }
}

function closeLoadingModal() {
  return { loading: false, message: defaultLoadingMessage }
}

const actionTypes = {
  OPEN: 'open',
  CLOSE: 'close',
}

function reducer(state, action) {
  const { payload } = action
  switch (action.type) {
    case actionTypes.OPEN:
      return openLoadingModal(state, payload)
    case actionTypes.CLOSE:
      return closeLoadingModal(state)
    default:
      throw new Error('Invalid Action')
  }
}

export default function LoadingProvider(props) {
  const [state, dispatch] = useReducer(reducer, initialModalState)

  return (
    <LoadingDispatchContext.Provider value={dispatch}>
      <LoadingStateContext.Provider value={state}>
        <LoadingModal state={state} dispatch={dispatch} />
        {props.children}
      </LoadingStateContext.Provider>
    </LoadingDispatchContext.Provider>
  )
}

function useLoadingContext() {
  const state = useContext(LoadingStateContext)
  const dispatch = useContext(LoadingDispatchContext)
  if (state === undefined || dispatch === undefined) {
    throw new Error('no context available')
  }
  return [state, dispatch]
}

const withLoadingContext = (Component) => (props) => {
  const [state, dispatch] = useLoadingContext()
  return <Component dispatch={dispatch} state={state} {...props} />
}

export { useLoadingContext, withLoadingContext }
