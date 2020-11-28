export const cmTags = {
  '!top': ['Resource'],
  Tag: {
    attrs: { name: null, color: null },
  },
  Desc: {
    children: ['Tag'],
  },
  Sql: {
    attrs: {
      id: null,
      ref: null,
      requires: null,
      when: null,
      onblank: null,
      onerror: null,
      classname: null,
      output: ['true', 'false'],
      limit: null,
      offset: null,
      type: ['query', 'update'],
      datasource: null,
    },
  },
  Transaction: {
    attr: { datasource: null },
    children: ['Sql'],
  },
  Arg: {
    attrs: {
      name: null,
      value: null,
      path: null,
    },
  },
  Execute: {
    attrs: {
      id: null,
      requires: null,
      when: null,
      onerror: null,
      classname: null,
      output: ['true', 'false'],
    },
    children: ['Arg'],
  },
  Script: {
    attrs: {
      id: null,
      output: ['true', 'false'],
      file: null,
      when: null,
    },
  },
  Param: {
    attrs: {
      name: null,
      max: null,
      min: null,
      maxlength: null,
      minlength: null,
      pattern: null,
      exists: null,
      value: null,
      required: ['true', 'false'],
      type: ['date', 'datetime', 'email', 'number', 'text', 'time', 'url'],
    },
  },
  Header: {
    attrs: {
      name: null,
      value: null,
    },
  },
  Body: {},
  XRequest: {
    attrs: {
      id: null,
      when: null,
      url: null,
      classname: null,
      method: ['GET', 'POST', 'PUT', 'DELETE'],
      output: ['true', 'false', 'headers'],
    },
    children: ['Param', 'Header', 'Body'],
  },
  Text: {
    attrs: { id: null, output: ['true', 'false'], when: null },
  },
  Request: {
    attrs: {
      method: ['GET', 'POST', 'PUT', 'DELETE'],
      status: null,
      item: ['true', 'false'],
    },
    children: [
      'Desc',
      'Header',
      'Param',
      'Sql',
      'Execute',
      'XRequest',
      'Text',
      'Script',
    ],
  },
  Resource: {
    attrs: {
      version: null,
      auth: null,
      parent: null,
    },
    children: ['Desc', 'Request'],
  },
}
