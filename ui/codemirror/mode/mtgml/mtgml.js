(function(mod) {
  if (typeof exports === "object" && typeof module === "object") // CommonJS
    mod(require("../../lib/codemirror"));
  else if (typeof define === "function" && define.amd) // AMD
    define(["../../lib/codemirror"], mod);
  else // Plain browser env
    mod(CodeMirror);
})(function(CodeMirror) {


const htmlConfig = {
    autoSelfClosers: {'area': true, 'base': true, 'br': true, 'col': true, 'command': true,
                      'embed': true, 'frame': true, 'hr': true, 'img': true, 'input': true,
                      'keygen': true, 'link': true, 'meta': true, 'param': true, 'source': true,
                      'track': true, 'wbr': true, 'menuitem': true},
    implicitlyClosed: {'dd': true, 'li': true, 'optgroup': true, 'option': true, 'p': true,
                       'rp': true, 'rt': true, 'tbody': true, 'td': true, 'tfoot': true,
                       'th': true, 'tr': true},
    contextGrabbers: {
        'dd': {'dd': true, 'dt': true},
        'dt': {'dd': true, 'dt': true},
        'li': {'li': true},
        'option': {'option': true, 'optgroup': true},
        'optgroup': {'optgroup': true},
        'p': {'address': true, 'article': true, 'aside': true, 'blockquote': true, 'dir': true,
              'div': true, 'dl': true, 'fieldset': true, 'footer': true, 'form': true,
              'h1': true, 'h2': true, 'h3': true, 'h4': true, 'h5': true, 'h6': true,
              'header': true, 'hgroup': true, 'hr': true, 'menu': true, 'nav': true, 'ol': true,
              'p': true, 'pre': true, 'section': true, 'table': true, 'ul': true},
        'rp': {'rp': true, 'rt': true},
        'rt': {'rp': true, 'rt': true},
        'tbody': {'tbody': true, 'tfoot': true},
        'td': {'td': true, 'th': true},
        'tfoot': {'tbody': true},
        'th': {'td': true, 'th': true},
        'thead': {'tbody': true, 'tfoot': true},
        'tr': {'tr': true}
    },
    doNotIndent: {"pre": true},
    allowUnquoted: true,
    allowMissing: true,
    caseFold: true
}

const xmlConfig = {
    autoSelfClosers: {},
    implicitlyClosed: {},
    contextGrabbers: {},
    doNotIndent: {},
    allowUnquoted: false,
    allowMissing: false,
    caseFold: false
}

const sqlKeywords = ['SELECT','WHERE','FROM','LIMIT','LEFT','RIGHT','JOIN','OFFSET',
    'AND','OR','INNER','OUTER','ORDER','BY','AS','GROUP','ON','LIKE','HAVING',
    'DELETE','INTO','INSERT','UPDATE','IN','VALUES','TABLE','DROP','DESC','ASC',
    'ADD','ALTER','COLUMN','MODIFY','CREATE','DEFAULT','EXISTS','PROC','DATABASE',
    'CURRENT_TIMESTAMP','CURRENT_DATE','CURRENT_TIME','BETWEEN','COALESCE','SET',
    'CONSTRAINT','DISTINCT','UNIQUE','NULL','IS','NOT','VIEW','TOP','CHECK','CONTAINS',

    'EXIT','PRINT','WRITETEXT','PRIMARY','WITHIN','WITH','PRECISION','EXEC','EXECUTE',
    'EXCEPT','PLAN','WHILE','PIVOT','WHEN','PERCENT','ERRLVL','WAITFOR','OVER','END',
    'ELSE','VARYING','DUMP','USER','DOUBLE','OPTION','DISTRIBUTED','OPENXML','USE',
    'OPENROWSET','UPDATETEXT','OPENQUERY','DISK','UNPIVOT','OPENDATASOURCE','OPEN','DENY',
    'UNIQUE','UNION','TSEQUAL','OFFSETS','CONTAINSTABLE','KEY','SHUTDOWN','CONTINUE','KILL',
    'SOME','CONVERT','LEFT','STATISTICS','SYSTEM_USER','CROSS','LINENO','CURRENT','LOAD',
    'TABLESAMPLE','MERGE','TEXTSIZE','NATIONAL','THEN', 'NOCHECK','TO','CURRENT_USER',
    'NONCLUSTERED','CURSOR','TRANSACTION','TRAN','DBCC','NULLIF','TRIGGER','DEALLOCATE',
    'OF','TRUNCATE','DECLARE','OFF','TRY_CONVERT','BACKUP','FROM','RESTORE','BEGIN','FULL',
    'RESTRICT','FUNCTION','RETURN','BREAK','GOTO','REVERT','BROWSE','GRANT','REVOKE','BULK',
    'RIGHT','ROLLBACK','CASCADE','HOLDLOCK','ROWCOUNT','CASE','IDENTITY','ROWGUIDCOL',
    'IDENTITY_INSERT','RULE','CHECKPOINT','IDENTITYCOL','SAVE','CLOSE','IF','SCHEMA',
    'CLUSTERED','SECURITYAUDIT','INDEX','COLLATE','SEMANTICKEYPHRASETABLE','COLUMN',
    'SEMANTICSIMILARITYDETAILSTABLE','COMMIT','INTERSECT','SEMANTICSIMILARITYTABLE',
    'COMPUTE','SESSION_USER','CONTAINS','SETUSER','EXTERNAL','PROCEDURE','ALL','FETCH',
    'PUBLIC','FILE','RAISERROR','FILLFACTOR','READ','ANY','FOR','READTEXT','FOREIGN',
    'RECONFIGURE','FREETEXT','REFERENCES','AUTHORIZATION','FREETEXTTABLE','REPLICATION'
];

let sqlTagType;
// var inSqlKeyword = "";
// var inSqlKeywordIndex = -1;
// var inSql = false

let inSpaceSql = false;
let inAllowedMqlPreChar = false;
let inMqlParam = false;

CodeMirror.defineMode("mtgml", function(editorConf, config_) {
    const indentUnit = editorConf.indentUnit
    const config = {}
    const defaults = config_.htmlMode ? htmlConfig : xmlConfig
    for (var prop in defaults) config[prop] = defaults[prop]
    for (var prop in config_) config[prop] = config_[prop]

    // Return variables for tokenizers
    let type; let setStyle;
    
    function inSqlQuote(quote) {
        const closure = function(stream, state) {
            while (!stream.eol()) {
                if (stream.next() == quote) {
                    state.tokenize = inText;
                    break;
                }
            }
            return "sqlstring";
        };
        closure.isInAttribute = true;
        return closure;
    }

    function inSqlPin(quote) {
        const closure = function(stream, state) {
            while (!stream.eol()) {
                if (stream.next() == quote) {
                    state.tokenize = inText;
                    break;
                }
            }
            return "sqlpin";
        };
        closure.isInAttribute = true;
        return closure;
    }

    function inSqlNumber() {
        const closure = function(stream, state) {
            while (!stream.eol()) {
                const next = stream.next()
                const p = stream.peek()
                if ( ( isNaN(Number(p)) && (p != '.') ) ||
                        (p == ' ') ) {
                    state.tokenize = inText;
                    break;
                }
            }
            return "sqlnumber";
        };
        closure.isInAttribute = true;
        return closure;
    }

    function sqlText(stream,state) {
        // console.log("sqlText: "+stream.peek())
       
        // look for strings inside quotes (`,",')
        if (/[\'\"]/.test(stream.peek())) {
            var ch1 = stream.next()
            state.tokenize = inSqlQuote(ch1);
            state.stringStartCol = stream.column();
            return state.tokenize(stream, state);
        } if (/[\`]/.test(stream.peek())) {
            var ch1 = stream.next()
            state.tokenize = inSqlPin(ch1);
            state.stringStartCol = stream.column();
            return state.tokenize(stream, state);
        } if(((stream.peek()=="$") && (inSpaceSql||inAllowedMqlPreChar))
                    || inMqlParam) {
            // look for $mqlParam
            var ch1 = stream.next();
            const p = stream.peek()
            if(((p == "<")||(p == " ")||(p == ")")||(p == ";")||(p == ","))) {
                
                inMqlParam = false;
            }
            else
                inMqlParam = true;

            return 'mqlparam'
        } if((Number(stream.peek()))&&(inSpaceSql||inAllowedMqlPreChar)) {
            state.tokenize = inSqlNumber();
            state.stringStartCol = stream.column();
            return state.tokenize(stream, state);

        } 
            // look for keywords and return style
            for(let iterator=0; iterator<sqlKeywords.length; iterator++ ) {
                const kw = sqlKeywords[iterator];

                /* if( (stream.match(kw+" ",false,true)&&inSpaceSql) || 
                        ( inSqlKeyword.toLowerCase() == kw.toLowerCase() ) ) {

                    inSqlKeyword = kw;
                    var ch1 = stream.next();
                    inSqlKeywordIndex++;
                    if(inSqlKeywordIndex == (kw.length-1)) {
                        inSqlKeyword = "";
                        inSqlKeywordIndex = -1;
                    }
                    return "sqlkeyword"
                } */
                if( (stream.match(`${kw} `,true,true)&&inSpaceSql) ) {
                    return "sqlkeyword"
                }
            }
        

        const ch = stream.next()
        if(ch == " ")
            inSpaceSql = true;
        else
            inSpaceSql = false;

        if((ch == "=")||(ch == "(")||(ch == ","))
            inAllowedMqlPreChar = true;
        else
            inAllowedMqlPreChar = false  

        return null
    }


    function inText(stream, state) {
        function chain(parser) {
            state.tokenize = parser;
            return parser(stream, state);
        }

        if(state.inSql && (stream.peek() != "<")) {
            return sqlText(stream,state)
        }

        const ch = stream.next();     

        if (ch == "<") {
            if (stream.eat("!")) {
                if (stream.eat("[")) {
                  if (stream.match("CDATA[")) return chain(inBlock("atom", "]]>"));
                  return null;
                } if (stream.match("--")) {
                  return chain(inBlock("comment", "-->"));
                } if (stream.match("DOCTYPE", true, true)) {
                  stream.eatWhile(/[\w\._\-]/);
                  return chain(doctype(1));
                } 
                  return null;
                
            } if (stream.eat("?")) {
                stream.eatWhile(/[\w\._\-]/);
                state.tokenize = inBlock("meta", "?>");
                return "meta";
            } 
                type = stream.eat("/") ? "closeTag" : "openTag";
                sqlTagType = type;
                state.tokenize = inTag;
                return "tag bracket";
            
        } if (ch == "&") {
            let ok;
            if (stream.eat("#")) {
                if (stream.eat("x")) {
                  ok = stream.eatWhile(/[a-fA-F\d]/) && stream.eat(";");
                } else {
                  ok = stream.eatWhile(/[\d]/) && stream.eat(";");
                }
            } else {
                ok = stream.eatWhile(/[\w\.\-:]/) && stream.eat(";");
            }
            return ok ? "atom" : "error";
        } 
            stream.eatWhile(/[^&<]/);
            return null;
        
    }
    inText.isInText = true;

    function inTag(stream, state) {
        if(stream.match("Query",false,false) || stream.match("Update",false,false)) { 
            if(sqlTagType == "openTag") {
                inSpaceSql = true
                state.inSql = true
            } else if(sqlTagType == "closeTag") {
                state.inSql = false
            }
        }

        const ch = stream.next();

        if (ch == ">" || (ch == "/" && stream.eat(">"))) {
            state.tokenize = inText;
            type = ch == ">" ? "endTag" : "selfcloseTag";
            return "tag bracket";
        } if (ch == "=") {
            type = "equals";
            return null;
        } if (ch == "<") {
            state.tokenize = inText;
            state.state = baseState;
            state.tagName = state.tagStart = null;
            const next = state.tokenize(stream, state);
            return next ? `${next  } tag error` : "tag error";
        } if (/[\'\"]/.test(ch)) {
            state.tokenize = inAttribute(ch);
            state.stringStartCol = stream.column();
            return state.tokenize(stream, state);
        } 
            stream.match(/^[^\s\u00a0=<>\"\']*[^\s\u00a0=<>\"\'\/]/);
            return "word";
        
    }

    function inAttribute(quote) {
      const closure = function(stream, state) {
        while (!stream.eol()) {
          if (stream.next() == quote) {
            state.tokenize = inTag;
            break;
          }
        }
        return "string";
      };
      closure.isInAttribute = true;
      return closure;
    }

    function inBlock(style, terminator) {
      return function(stream, state) {
        while (!stream.eol()) {
          if (stream.match(terminator)) {
            state.tokenize = inText;
            break;
          }
          stream.next();
        }
        return style;
      };
    }

    function doctype(depth) {
      return function(stream, state) {
        let ch;
        while ((ch = stream.next()) != null) {
          if (ch == "<") {
            state.tokenize = doctype(depth + 1);
            return state.tokenize(stream, state);
          } if (ch == ">") {
            if (depth == 1) {
              state.tokenize = inText;
              break;
            } else {
              state.tokenize = doctype(depth - 1);
              return state.tokenize(stream, state);
            }
          }
        }
        return "meta";
      };
    }

    function Context(state, tagName, startOfLine) {
        this.prev = state.context;
        this.tagName = tagName;
        this.indent = state.indented;
        this.startOfLine = startOfLine;
        if (config.doNotIndent.hasOwnProperty(tagName) 
                || (state.context && state.context.noIndent))
            this.noIndent = true;
    }
    function popContext(state) {
        if (state.context) state.context = state.context.prev;
    }
    function maybePopContext(state, nextTagName) {
        let parentTagName;
        while (true) {
            if (!state.context) {
              return;
            }
            parentTagName = state.context.tagName;
            if (!config.contextGrabbers.hasOwnProperty(parentTagName) ||
                !config.contextGrabbers[parentTagName].hasOwnProperty(nextTagName)) {
              return;
            }
            popContext(state);
        }
    }

    function baseState(type, stream, state) {
      if (type == "openTag") {
          state.tagStart = stream.column();
          return tagNameState;
      } if (type == "closeTag") {
          return closeTagNameState;
      } 
          return baseState;
      
    }
    function tagNameState(type, stream, state) {
        if (type == "word") {
            state.tagName = stream.current();
            setStyle = "tag";
            return attrState;
        } 
            setStyle = "error";
            return tagNameState;
        
    }
    function closeTagNameState(type, stream, state) {
        if (type == "word") {
            const tagName = stream.current();
            if (state.context && state.context.tagName != tagName &&
                config.implicitlyClosed.hasOwnProperty(state.context.tagName))
              popContext(state);
            if ((state.context && state.context.tagName == tagName) || config.matchClosing === false) {
              setStyle = "tag";
              return closeState;
            } 
              setStyle = "tag error";
              return closeStateErr;
            
        } 
            setStyle = "error";
            return closeStateErr;
        
    }

    function closeState(type, _stream, state) {
        if (type != "endTag") {
            setStyle = "error";
            return closeState;
        }
        popContext(state);
        return baseState;
    }
    function closeStateErr(type, stream, state) {
        setStyle = "error";
        return closeState(type, stream, state);
    }

    function attrState(type, _stream, state) {
        if (type == "word") {
            setStyle = "attribute";
            return attrEqState;
        } if (type == "endTag" || type == "selfcloseTag") {
            const tagName = state.tagName; const tagStart = state.tagStart;
            state.tagName = state.tagStart = null;
            if (type == "selfcloseTag" ||
                    config.autoSelfClosers.hasOwnProperty(tagName)) {
                maybePopContext(state, tagName);
            } else {
                maybePopContext(state, tagName);
                state.context = new Context(state, tagName, tagStart == state.indented);
            }
            return baseState;
        }
        setStyle = "error";
        return attrState;
    }
    function attrEqState(type, stream, state) {
        if (type == "equals") return attrValueState;
        if (!config.allowMissing) setStyle = "error";
        return attrState(type, stream, state);
    }
    function attrValueState(type, stream, state) {
        if (type == "string") return attrContinuedState;
        if (type == "word" && config.allowUnquoted) {setStyle = "string"; return attrState;}
        setStyle = "error";
        return attrState(type, stream, state);
    }
    function attrContinuedState(type, stream, state) {
        if (type == "string") return attrContinuedState;
        return attrState(type, stream, state);
    }

    return {
        startState(baseIndent) {
            const state = {tokenize: inText,
                         state: baseState,
                         indented: baseIndent || 0,
                         tagName: null, tagStart: null,
                         context: null, inSql: false}
            if (baseIndent != null) state.baseIndent = baseIndent
            return state
        },

        token(stream, state) {
            if (!state.tagName && stream.sol()) {
                state.indented = stream.indentation();
            }

            if(!state.inSql) {
                if (stream.eatSpace()) return null;
            }
            
            type = null;
            let style = state.tokenize(stream, state);
            if ((style || type) && style != "comment") {
                setStyle = null;
                state.state = state.state(type || style, stream, state);
                if (setStyle)
                    style = setStyle == "error" ? `${style  } error` : setStyle;
            }
            return style;
        },

        indent(state, textAfter, fullLine) {
            let context = state.context;
            // Indent multi-line strings (e.g. css).
            if (state.tokenize.isInAttribute) {
                if (state.tagStart == state.indented)
                  return state.stringStartCol + 1;
                return state.indented + indentUnit;
            }
            if (context && context.noIndent) return CodeMirror.Pass;
            
            if (state.tokenize != inTag && state.tokenize != inText)
                return fullLine ? fullLine.match(/^(\s*)/)[0].length : 0;
            // Indent the starts of attribute names.
            if (state.tagName) {
                if (config.multilineTagIndentPastTag !== false)
                  return state.tagStart + state.tagName.length + 2;
                return state.tagStart + indentUnit * (config.multilineTagIndentFactor || 1);
            }
            if (config.alignCDATA && /<!\[CDATA\[/.test(textAfter)) return 0;
            const tagAfter = textAfter && /^<(\/)?([\w_:\.-]*)/.exec(textAfter);
            if (tagAfter && tagAfter[1]) { // Closing tag spotted
                while (context) {
                    if (context.tagName == tagAfter[2]) {
                      context = context.prev;
                      break;
                    } else if (config.implicitlyClosed.hasOwnProperty(context.tagName)) {
                      context = context.prev;
                    } else {
                      break;
                    }
                }
            } else if (tagAfter) { // Opening tag spotted
                while (context) {
                    const grabbers = config.contextGrabbers[context.tagName];
                    if (grabbers && grabbers.hasOwnProperty(tagAfter[2]))
                      context = context.prev;
                    else
                      break;
                }
            }
            while (context && context.prev && !context.startOfLine)
                context = context.prev;
            if (context) return context.indent + indentUnit;
            return state.baseIndent || 0;
        },

        electricInput: /<\/[\s\w:]+>$/,
        blockCommentStart: "<!--",
        blockCommentEnd: "-->",

        configuration: config.htmlMode ? "html" : "mtgml",
        helperType: config.htmlMode ? "html" : "mtgml",

        skipAttribute(state) {
            if (state.state == attrValueState)
                state.state = attrState
        }
    };
});

CodeMirror.defineMIME("text/mtgml", "mtgml");
CodeMirror.defineMIME("application/mtgml", "mtgml");
if (!CodeMirror.mimeModes.hasOwnProperty("text/html"))
  CodeMirror.defineMIME("text/html", {name: "mtgml", htmlMode: true});

});
