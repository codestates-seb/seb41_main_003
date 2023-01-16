import SignUpForm from '../components/MainSection/SignUpForm';
import styles from './SignUp.module.css';

const SignUp = () => {
  return (
    <div className={styles.wrapper}>
      <div className={styles.container}>
        <SignUpForm />
      </div>
    </div>
  );
};

export default SignUp;
