import React from 'react';

class EditMenu extends React.Component {

  constructor(props) {
    super(props);
    this.state = {

    };

    this.setReadonlyMode = this.props.setReadonlyMode.bind(this);

    this.handleMoveCellUp = this.handleMoveCellUp.bind(this);
    this.handleMoveCellDown = this.handleMoveCellDown.bind(this);
    this.handleInsertCodeCell = this.handleInsertCodeCell.bind(this);
    this.handleInsertMarkdownCell = this.handleInsertMarkdownCell.bind(this);
    this.handleRemoveCell = this.handleRemoveCell.bind(this);
  }

  handleMoveCellUp() {
    this.props.moveCellUp();
  }

  handleMoveCellDown() {
    this.props.moveCellDown();
  }

  handleInsertCodeCell() {
    this.props.insertCell('CODE');
  }

  handleInsertMarkdownCell() {
    this.props.insertCell('MARKDOWN');
  }

  handleRemoveCell() {
    this.props.removeCell();
  }

  render() {
    return (
      <div id='mjnb-edit-menu' className='mjnb-menu'>
        <div id='edit-menu-header' className='menu-header'>
          <div className='menu-header-col'>
            <div className='menu-header-tab' onClick={ this.setReadonlyMode }>
              <p>View Mode</p>
            </div>
          </div>
          <div className='menu-header-col'>
            <div className='menu-header-tab menu-header-selected'>
              <p>Edit Mode</p>
            </div>
          </div>
        </div>
        <div className='menu-content'>
          <div className='menu-row' onClick={ this.handleMoveCellUp }>
            <p><i className='menu-icon fa fa-arrow-up fa-inverse' aria-hidden='true' /> Move Cell Up</p>
          </div>
          <div className='menu-row' onClick={ this.handleMoveCellDown }>
            <p><i className='menu-icon fa fa-arrow-down fa-inverse' aria-hidden='true' /> Move Cell Down</p>
          </div>
          <div className='menu-row' onClick={ this.handleInsertCodeCell }>
            <p><i className='menu-icon fa fa-code' aria-hidden='true' /> Insert Code Cell Below</p>
          </div>
          <div className='menu-row' onClick={ this.handleInsertMarkdownCell }>
            <p><i className='menu-icon fa fa-file-text' aria-hidden='true' /> Insert Markdown Cell Below</p>
          </div>
          <div className='menu-row' onClick={ this.handleRemoveCell }>
            <p><i className='menu-icon fa fa-ban fa-inverse' aria-hidden='true' /> Remove Cell</p>
          </div>
        </div>
      </div>
    );
  }
}

export default EditMenu;
