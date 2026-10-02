// legacy Groovy script: request parameters are bound as _$name, output goes in response
response['message'] = 'Hello ' + _$who
response['length'] = _$who.length()
