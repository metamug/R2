export function setParams(queries) {
  // Queries will be object
  //   const queries = {
  //     name: 'somename',
  //     version: 'someversion',
  //   }
  const searchParams = new URLSearchParams()
  Object.keys(queries).map((key) => searchParams.set(key, queries[key]))
  return searchParams.toString()
}

export function getParams(query) {
  // query should be location.search value ex. '?query=react&name=somename'
  const searchParams = new URLSearchParams(query)
  const response = {}
  for (const key of searchParams.keys()) {
    response[key] = searchParams.get(key)
  }
  return response
}
