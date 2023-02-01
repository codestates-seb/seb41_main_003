import UserInfoForm from '../components/MainSection/UserInfoForm';
import styles from './UserInfo.module.css';
const UserInfo = () => {
  return (
    <div className={styles.wrapper}>
      <div className={styles.container}>
        <UserInfoForm />
      </div>
    </div>
  );
};

export default UserInfo;
