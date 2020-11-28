module.exports = {
  env: {
    browser: true,
    es6: true,
  },
  extends: ['eslint:recommended', 'plugin:react/recommended', 'prettier'],
  globals: {
    Atomics: 'readonly',
    SharedArrayBuffer: 'readonly',
  },
  parserOptions: {
    ecmaFeatures: {
      jsx: true,
    },
    ecmaVersion: 2018,
    sourceType: 'module',
  },
  plugins: ['react'],
  ignorePatterns: ['assets/vendor/css/vendor.min.css'],
  rules: {
    'no-cond-assign': 0,
    'no-empty': 0,
    'react/prop-types': 0,
    'no-unused-vars': 'warn',
    'no-debugger': 'warn',
    'react/display-name': 0,
  },
}
