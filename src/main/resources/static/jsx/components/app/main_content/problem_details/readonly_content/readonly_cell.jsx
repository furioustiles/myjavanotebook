import React from 'react';
import CodeMirror from 'react-codemirror';
import ReactMarkdown from 'react-markdown';

require('codemirror/mode/clike/clike');

class ReadonlyCell extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      id: this.props.id,
      type: this.props.type,
      content: this.props.content
    };
  }

  render() {
    let cellContent;
    const codemirrorOptions = {
      lineNumbers: false,
      readOnly: true,
      mode: 'text/x-java',
      viewPortMargin: Infinity
    };

    if (this.state.type === 'CODE') {
      cellContent = (
        <CodeMirror
            value={ this.state.content }
            options={ codemirrorOptions }
        />
      );
    } else if (this.state.type === 'MARKDOWN') {
      cellContent = (
        <ReactMarkdown
            source={ this.state.content }
        />
      );
    }

    return (
      <div id={ this.state.id } className='cell readonly-cell'>
        { cellContent }
      </div>
    );
  }
}

export default ReadonlyCell;
