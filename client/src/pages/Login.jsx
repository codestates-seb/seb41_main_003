import LoginForm from '../components/MainSection/LoginForm';
import styles from './Login.module.css';

const Login = () => {
  return (
    <div className={styles.wrapper}>
      <div className={styles.container}>
        <LoginForm />
      </div>
    </div>
  );
};

export default Login;
