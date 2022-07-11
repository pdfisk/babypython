# 01_chicklet_first_steps.py
#
# Chicklet is learning to walk.
#
# Updated: 2022-07-08T15:08:23Z


# assign variables
chicklet = Chick('Chicklet')
col = 0
row = 0

# say hello
chicklet.say('Hi There!<br>My name is Chicklet.')
sleep(2)

# say move right
chicklet.say('I am going to walk right three steps.')
sleep(2)

# move right
while col < 3:
    col = col + 1
    chicklet.move_to(row, col)
    sleep(1)

# say move down
sleep(2)
chicklet.say('Now, I am going to walk down three steps.')
sleep(2)

# move down
while row < 3:
    row = row + 1
    chicklet.move_to(row, col)
    sleep(1)

# say all done
sleep(2)
chicklet.say("That's all for now folks!")
