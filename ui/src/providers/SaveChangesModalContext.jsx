// Wraps Entire app, check entry.js
import React, { createContext, useContext, useReducer } from 'react'
import SaveChangesModal from 'components/SaveChangesModal'

const SaveChangesStateContext = createContext()
const SaveChangesDispatchContext = createContext()

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

export default function SaveChangesModalProvider(props) {
  const [state, dispatch] = useReducer(reducer, initialModalState)

  return (
    <SaveChangesDispatchContext.Provider value={dispatch}>
      <SaveChangesStateContext.Provider value={state}>
        <SaveChangesModal state={state} dispatch={dispatch} />
        {props.children}
      </SaveChangesStateContext.Provider>
    </SaveChangesDispatchContext.Provider>
  )
}

function useSaveChangesModalContext() {
  const state = useContext(SaveChangesStateContext)
  const dispatch = useContext(SaveChangesDispatchContext)
  if (state === undefined || dispatch === undefined) {
    throw new Error('no context available')
  }
  return [state, dispatch]
}

const withSaveChangesModalContext = (Component) => (props) => {
  const [state, dispatch] = useSaveChangesModalContext()
  return <Component dispatch={dispatch} state={state} {...props} />
}

export { useSaveChangesModalContext, withSaveChangesModalContext }
