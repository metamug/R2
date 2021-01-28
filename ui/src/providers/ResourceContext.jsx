import React, { createContext, createRef, useContext, useState } from 'react'

const ResourceContext = createContext()

export const newResourceTitle = 'New Resource'

const initialResource = {
  name: newResourceTitle,
  version: '1.0',
  isNewResource: true,
}

export default function ResourceProvider(props) {
  const cmRef = createRef()

  const [selectedResource, setSelectedResource] = useState(initialResource)

  return (
    <ResourceContext.Provider
      value={{
        state: {
          selectedResource,
        },
        handlers: {
          setSelectedResource,
        },
        refs: { cmRef },
      }}
    >
      {props.children}
    </ResourceContext.Provider>
  )
}

function useResourceContext() {
  const context = useContext(ResourceContext)
  if (context === undefined) {
    throw new Error('Context Unavailable')
  }
  return [context.state, context.handlers, context.refs]
}

export { ResourceContext, useResourceContext }
