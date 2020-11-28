/* eslint-disable no-undef */
export const newResourceDefaultVal =
  '<?xml version="1.0" encoding="UTF-8" ?>\n' +
  '<Resource xmlns="http://xml.metamug.net/resource/1.0" v="1.0">\n\n' +
  '\t<Request method="GET">\n' +
  '\t\t<Desc> Example Resource </Desc>\n' +
  '\t\t<Sql id="myQuery"> SELECT \'Hello World\' </Sql>\n' +
  '\t</Request>\n\n' +
  '</Resource>'

export const API_URL_DOMAIN = process.env.REACT_APP_API_URL_DOMAIN || ''
export const API_URL_CONTEXT =
  process.env.REACT_APP_API_URL_CONTEXT || '/console'
export const API_URL = API_URL_DOMAIN + API_URL_CONTEXT
export const UTM =
  '?utm_source=metamug-api-console&utm_medium=link&utm_campaign=product-guide'
