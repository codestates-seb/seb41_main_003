import styles from './Loading.module.css';
import PropType from 'prop-types';

const Loading = ({ height = '100%' }) => {
  return (
    <div className={styles.container} style={{ height }}>
      <div className={`${styles.loadingSpinner}`}>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
      </div>
    </div>
  );
};

Loading.propTypes = {
  height: PropType.string,
};

export default Loading;
