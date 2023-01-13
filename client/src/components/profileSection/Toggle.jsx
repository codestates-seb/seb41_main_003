import styles from './Toggle.module.css';
import PropTypes from 'prop-types';

const Toggle = ({ modalOpenOnHandler, isAnnounceOn }) => {
  return (
    <div className={styles.toggleSwitch}>
      <button
        onClick={(e) => {
          e.preventDefault();
          modalOpenOnHandler();
        }}
      >
        <input
          className={styles.checkbox}
          type="checkbox"
          defaultChecked={isAnnounceOn === 'REQUEST' ? true : false}
        />
        <span
          className={`${styles.slider} ${
            isAnnounceOn === 'REQUEST' ? '' : styles.unActive
          }`}
        ></span>
      </button>
    </div>
  );
};

Toggle.propTypes = {
  modalOpenOnHandler: PropTypes.func,
  isAnnounceOn: PropTypes.string,
};

export default Toggle;
