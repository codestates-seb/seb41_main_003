import ChangeProfileCard from '../components/ChangeProfile/ChangeProfileCard';
import ChangeProfileContents from '../components/ChangeProfile/ChangeProfileContents';
import styles from './ChangeProfile.module.css';
import { useState } from 'react';

const initialState = {
  profile_id: 0,
  user_id: 0,
  name: '',
  rate: 0,
  bio: '',
  want_date: '',
  pay: '',
  way: '',
  profile_status: 'TUTOR',
  wanted_status: null,
  gender: '',
  pre_tutoring: '',
  difference: '',
  school: '',
  character: '',
  subjects: [],
};

const AddProfile = () => {
  const [user, setUser] = useState(initialState);
  return (
    <div className={styles.wrapper}>
      <div className={styles.container}>
        <ChangeProfileCard user={user} setUser={setUser} isNew={true} />
        <ChangeProfileContents user={user} setUser={setUser} />
      </div>
    </div>
  );
};

export default AddProfile;
