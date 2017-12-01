import React from 'react';

class ReadonlyMenu extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      showCodeOnly: this.props.showCodeOnly
    };

    this.setEditMode = this.props.setEditMode.bind(this);

    this.handleCheckboxClick = this.handleCheckboxClick.bind(this);
  }

  handleCheckboxClick() {
    this.props.toggleShowCodeOnly();
  }

  componentWillReceiveProps(newProps) {
    this.setState({
      showCodeOnly: newProps.showCodeOnly
    });
  }

  render() {
    const checkboxButton = (this.state.showCodeOnly)
        ? <i className='menu-icon fa fa-check-square-o' onClick={ this.handleCheckboxClick } />
        : <i className='menu-icon fa fa-square-o' onClick={ this.handleCheckboxClick } />;

    return (
      <div id='mjnb-readonly-menu' className='mjnb-menu'>
        <div id='readonly-menu-header' className='menu-header'>
          <div className='menu-header-col'>
            <div className='menu-header-tab menu-header-selected'>
              <p>View Mode</p>
            </div>
          </div>
          <div className='menu-header-col'>
            <div className='menu-header-tab' onClick={ this.setEditMode }>
              <p>Edit Mode</p>
            </div>
          </div>
        </div>
        <div className='menu-content'>
          <div className='menu-row-checkbox'>
            { checkboxButton }
            <label>Show code only </label>
          </div>
        </div>
      </div>
    );
  }
}

export default ReadonlyMenu;
