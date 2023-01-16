import PropType from 'prop-types';
import TutorContents from './TutorContents';
import TuteeContents from './TuteeContents';

const ChangeProfileContents = ({ user, setUser }) => {
  const { profileStatus } = user;

  return (
    <>
      {profileStatus === 'TUTOR' ? (
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
