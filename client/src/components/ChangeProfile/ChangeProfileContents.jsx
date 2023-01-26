import PropType from 'prop-types';
import TutorContents from './TutorContents';
import TuteeContents from './TuteeContents';
import Profile from '../../recoil/profile';

const ChangeProfileContents = ({ user, setUser }) => {
  const userStatus = sessionStorage.getItem('userStatus');

  return (
    <>
      {userStatus === 'TUTOR' ? (
        <TutorContents user={user} setUser={setUser} />
      ) : (
        <TuteeContents user={user} setUser={setUser} />
      )}
    </>
  );
};

ChangeProfileContents.propTypes = {
  user: PropType.object,
  setUser: PropType.func,
};

export default ChangeProfileContents;
