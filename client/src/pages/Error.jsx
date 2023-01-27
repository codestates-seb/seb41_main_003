import styles from './Error.module.css';
import { MdError } from 'react-icons/md';

const Error = () => {
  return (
    <div className={styles.wrapper}>
      <div className={styles.container}>
        <div className={styles.errorMsg}>
          <MdError className={styles.errorIcon} />
          <span>존재하지 않는 페이지입니다.</span>
          <span>주소창을 확인해주세요.</span>
        </div>
      </div>
    </div>
  );
};

export default Error;
