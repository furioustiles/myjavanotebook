import React from 'react';
import CodeMirror from 'react-codemirror';

require('codemirror/mode/clike/clike');
require('codemirror/mode/markdown/markdown');

class EditCell extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      id: this.props.id,
      type: this.props.type,
      content: this.props.content,
      focusedCell: this.props.focusedCell,
      focusedCellIndex: this.props.focusedCellIndex
    };

    this.handleCodeChange = this.handleCodeChange.bind(this);
    this.handleFocusChange = this.handleFocusChange.bind(this);
  }

  handleCodeChange(newCode) {
    this.setState({
      content: newCode
    }, () => {
      this.props.updateCell(this.state.focusedCellIndex, this.state.content);
    });
  }

  handleFocusChange() {
    this.props.changeFocusedCell(this.state.id);
  }

  componentWillReceiveProps(newProps) {
    this.setState({
      id: newProps.id,
      type: newProps.type,
      content: newProps.content,
      focusedCell: newProps.focusedCell,
      focusedCellIndex: newProps.focusedCellIndex
    });
  }

  render() {
    const codemirrorOptions = {
      lineNumbers: false,
      readOnly: false,
      mode: (this.state.type === 'CODE') ? 'text/x-java' : 'markdown',
      viewportMargin: Infinity
    };

    if (this.state.id === this.state.focusedCell) {
      return (
        <div id={ this.state.id } className='cell edit-cell edit-cell-focused'>
          <div className='edit-cell-type'>
            { this.state.type }
          </div>
          <CodeMirror
              ref='editor'
              value={ this.state.content }
              options={ codemirrorOptions }
              onChange={ this.handleCodeChange }
          />
        </div>
      );
    }

    return (
      <div id={ this.state.id } className='cell edit-cell'>
        <div className='edit-cell-type'>
          { this.state.type }
        </div>
        <CodeMirror
            ref='editor'
            value={ this.state.content }
            options={ codemirrorOptions }
            onChange={ this.handleCodeChange }
            onFocusChange={ this.handleFocusChange }
        />
      </div>
    );
  }
}

export default EditCell;
