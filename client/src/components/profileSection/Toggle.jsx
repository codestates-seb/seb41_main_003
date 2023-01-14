import styles from './Toggle.module.css';
import PropTypes from 'prop-types';
const Toggle = ({ setIsModalOpen, setIsAnnounceOn }) => {
  return (
    <div className={styles.toggleSwitch}>
      <label>
        <input
          type="checkbox"
          onClick={() => {
            setIsModalOpen((prev) => !prev), setIsAnnounceOn((prev) => prev);
          }}
        />
        <span className={styles.slider}></span>
      </label>
    </div>
  );
};
Toggle.propTypes = {
  setIsModalOpen: PropTypes.func,
  setIsAnnounceOn: PropTypes.func,
};
export default Toggle;
