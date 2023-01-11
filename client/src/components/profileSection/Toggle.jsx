import styles from './Toggle.module.css';
import PropTypes from 'prop-types';
const Toggle = ({ modalOpenOnHandler }) => {
  return (
    <div className={styles.toggleSwitch}>
      <label>
        <input
          type="checkbox"
          onClick={() => {
            modalOpenOnHandler();
          }}
        />
        <span className={styles.slider}></span>
      </label>
    </div>
  );
};
Toggle.propTypes = {
  modalOpenOnHandler: PropTypes.func,
};
export default Toggle;
