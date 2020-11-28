const monthNames = [
  'Jan',
  'Feb',
  'Mar',
  'Apr',
  'May',
  'Jun',
  'Jul',
  'Aug',
  'Sep',
  'Oct',
  'Nov',
  'Dec',
]

export function formatDate(date) {
  const day = date.getDate()
  const monthIndex = date.getMonth()
  const month = monthNames[monthIndex]
  const year = date.getFullYear() % 100
  const hours = date.getHours()
  const minutes = (date.getMinutes() < 10 ? '0' : '') + date.getMinutes()
  return `${month} ${day} '${year}  ${hours}:${minutes}`
}

export function getPrintableDate(val) {
  // val "2017-06-20 15:17:24.0"
  const date = new Date(val)
  return formatDate(date)
}
